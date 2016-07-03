from nile_backend.models import *
import requests
import json


def distance_to_time(distance):
  return distance*30

def request_purchaser(user):
  url = 'https://fcm.googleapis.com/fcm/send'
  to = user.token
  payload = {"to":to,"notification":{"title":"CheckDisOut","text":"Moin"}}
  headers = {"Authorization":"key=AIzaSyAhfw6N9h9j0dGW11Xe_IRaTIK0fsiHwvU","Content-Type":"application/json"}

  request = requests.post(url, data=json.dumps(payload), headers=headers)
  print(request)

def resolve_recipient(user):
  #Get users in neigborhood

  addresses = Address.objects.all()
  user_addr = Address.objects.get(user=user)
  user_home_loc = user_addr.location
  user_loc = user.locations.last()
  distance_to_home = user_home_loc.distance_to(user_loc)
  user_at_home = True if distance_to_home<0.050 else False
  user_awaits_package = Package.objects.filter(purchaser=user).exists()

  for address in addresses:  
    if address.location.distance_to(user_loc) < 0.200:
      neigbor = address.user
      neigbor_packages = Packages.objects.filter(user=neigbor)
      for pack in neigbor_packages:
        pur_home = Address.object.get(user=pack.purchaser).location
        if user_at_home:
          if pack.recipient == null:
            pack.recipient = user
          elif Address.objects.get(user=pack.recipient).location.distance_to(pur_home)>user_loc.distance_to(pur_home):
            pack.recipient = user
          pack.save()
      
      if user_awaits_package and not user_at_home:
        url = 'https://fcm.googleapis.com/fcm/send'
        to = neigbor.token
        payload = {"to":to,"notification":{"title":"CheckDisOut","text":"Moin"}}
        headers = {"Authorization":"key=AIzaSyAhfw6N9h9j0dGW11Xe_IRaTIK0fsiHwvU","Content-Type":"application/json"}

        request = requests.post(url, data=json.dumps(payload), headers=headers)
        print(request)
