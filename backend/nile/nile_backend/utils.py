from math import radians, cos, sin, asin, sqrt
import requests

def haversine(lng1, lat1, lng2, lat2):
  """
  Calculate the great circle distance between two points 
  on the earth (specified in decimal degrees)
  """
  # convert decimal degrees to radians 
  lng1, lat1, lng2, lat2 = map(radians, [lng1, lat1, lng2, lat2])
  # haversine formula 
  dlon = lng2 - lng1 
  dlat = lat2 - lat1 
  a = sin(dlat/2)**2 + cos(lat1) * cos(lat2) * sin(dlng/2)**2
  c = 2 * asin(sqrt(a)) 
  km = 6367 * c
  return km

def distance_to_time(distance):
  return distance*30



def resolve_recipient(user):
  url = 'https://fcm.googleapis.com/fcm/send'
  to = 'der00QqQn7U:APA91bHNNPcVqZSLaBb8aeek_3bhzkWVjQHXe9k-4jGwQl2tGjsskpkyWzQaVxkcu8ywXck9xqjDMXyRsWZGbeoCbGkFLWurI_DIPe5so0HyGD3yx7TsKBQHpL2zeH2ZwGX3WFndKKi1'
  payload = {"to":to,"notification":{"title":"CheckDisOut","text":"Moin"}}
  headers = {"Authorization":"key=AIzaSyAhfw6N9h9j0dGW11Xe_IRaTIK0fsiHwvU","Content-Type":"application/json"}

  recipient_pos = requests.post(url, json=payload, headers=headers)
