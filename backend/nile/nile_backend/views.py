from django.shortcuts import render
from django.http import HttpResponse

from nile_backend.models import Package, User, Location, Address
from nile_backend.serializers import UserSerializer, PackageSerializer, LocationSerializer
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status, generics, mixins

def index(request):
  return HttpResponse("Hello, world. You're at the polls index.")

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
      return Response(location.errors, status=status.HTTP_400_BAD_REQUEST)
    location = serializer.save()
    user = User.objects.get(id=user_id)
    user.locations.add(location)
    user.save()
    
    if user.type=='deliverer':
      packages = Package.objects.all()
      for pack in packages:
        """
        Get home address of purchaser
        """
        purchaser = pack.purchaser
        address = Address.object.get(user=purchaser);
#        location.distance_to(
    return Response(serializer.data, status=status.HTTP_201_CREATED)



    
