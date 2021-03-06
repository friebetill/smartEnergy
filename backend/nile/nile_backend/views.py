from django.shortcuts import render, render_to_response
from django.http import HttpResponse

from nile_backend.models import Package, User, Location, Address
from nile_backend.serializers import *
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status, generics, mixins
from nile_backend.utils import *

def index(request):
  return render_to_response('nile_backend/index.html')

def cluster(request):
  return render_to_response('nile_backend/cluster.html')

class UserList(APIView):
  def get(self, request, format=None):
    users = User.objects.all()
    serializer = UserSerializer(users, many=True)
    return Response(serializer.data)

  def post(self, request, format=None):
    serializer = UserSerializer(data=request.data)
    if serializer.is_valid():
      serializer.save()
      return Response(serializer.data, status=status.HTTP_201_CREATED)
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

  def put(self, request, user_id):
    user = User.objects.get(id=user_id)
    user.token = request.body
    user.save()
    return Response( status=status.HTTP_201_CREATED)

class PackageList(generics.ListAPIView,
                  mixins.CreateModelMixin,
                  mixins.ListModelMixin):
  queryset = Package.objects.all()
  serializer_class = PackageSerializer

  def get_queryset(self):
    if 'user_id' in self.kwargs:
      user_id = self.kwargs['user_id']
      return Package.objects.filter(purchaser__id=user_id)
    return Package.objects.all()

  # def get(self, request, *args, **kwargs):
    # return self.list(request, *args, **kwargs)


class LocationList(generics.ListAPIView,
                   mixins.CreateModelMixin,
                   mixins.ListModelMixin):
  queryset = Location.objects.all()
  serializer_class = LocationSerializer

  def post(self, request, user_id):
    serializer = LocationSerializer(data=request.data)
    if not serializer.is_valid():
      return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
    location = serializer.save()
    user = User.objects.get(id=user_id)
    user.locations.add(location)
    user.save()
    print(user.locations.last())
    
    if user.type=='deliverer':
      packages = Package.objects.all()
      for pack in packages:
       # Get home address of purchaser
        purchaser = pack.purchaser
        address = None
        try:
          address = Address.objects.get(user=purchaser)
          distance = location.distance_to(address.location)
          print(distance)
          duration =  distance_to_time(distance)
          pack.mins__until_delivery = duration
          pack.save()
          if duration <= 15.0:
            request_purchaser(purchaser)
        except Address.DoesNotExist:
          pass
    if user.type=='client':
          resolve_recipient(user)

    return Response(serializer.data, status=status.HTTP_201_CREATED)

class AddressList(generics.ListAPIView,
                  mixins.CreateModelMixin,
                  mixins.ListModelMixin):
  queryset = Address.objects.all()
  serializer_class = AddressSerializer

  def post(self, request, user_id):
    serializer = AddressSerializer(data=request.data)
    if not serializer.is_valid():
      return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
    try:
      u = User.objects.get(id=user_id)
    except User.DoesNotExist:
      return Response('User does not exist.', status=status.HTTP_400_BAD_REQUEST)
    serializer.save(user=u)
    return Response(serializer.data, status=status.HTTP_201_CREATED)

class LastLocationList(generics.ListAPIView,
                       mixins.CreateModelMixin,
                       mixins.ListModelMixin):
  serializer_class = LocationSerializer

  def get(self, request, user_id):    
    latest_location = Location.objects.last()    
    serializer = LocationSerializer(latest_location, many=False)    
    return Response(serializer.data)


class DelivererLocationList(APIView):

  def get(self, request):
    deliverers = User.objects.filter(type=User.USER_DELIVERER)
    locations = []
    for d in deliverers:
      try:
        l = Location.objects.filter(user=d).latest('created_at')
        locations.append(l)
      except Location.DoesNotExist:
        pass
    # latest_location = Location.objects.filter(user__type=User.USER_DELIVERER)
    # return Response(locations[0])
    serializer = LocationSerializer(locations, many=True)
    return Response(serializer.data)

class ClientLocationList(APIView):

  def get(self, request):
    # clients = User.objects.filter(type=User.USER_CLIENT)
    clients = Location.objects.all()
    serializer = LocationSerializer(clients, many=True)
    return Response(serializer.data)

