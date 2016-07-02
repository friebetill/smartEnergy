from math import radians, cos, sin, asin, sqrt

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
