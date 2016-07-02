from django.db import models

class Location(models.Model):
  lat = models.DecimalField(max_digits=13, decimal_places=10)
  lng = models.DecimalField(max_digits=13, decimal_places=10)
  created_at = models.DateTimeField(auto_now_add=True)
  updated_at = models.DateTimeField(auto_now=True)

class User(models.Model):
  name = models.CharField(max_length=255)
  # type = models.CharField(max_length=255)
  locations = models.ManyToManyField(Location)
  created_at = models.DateTimeField(auto_now_add=True)
  updated_at = models.DateTimeField(auto_now=True)

class Address(models.Model):
  user = models.ForeignKey(User, on_delete=models.CASCADE)
  location = models.ForeignKey(Location, on_delete=models.CASCADE)
  name = models.CharField(max_length=255)
  postcode = models.CharField(max_length=31)
  city = models.CharField(max_length=255)
  country = models.CharField(max_length=255)
  floor = models.PositiveIntegerField(default=0)
  created_at = models.DateTimeField(auto_now_add=True)
  updated_at = models.DateTimeField(auto_now=True)
