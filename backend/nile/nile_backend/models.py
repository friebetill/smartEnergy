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

class Favorite(models.Model):
  owner = models.ForeignKey(User, related_name='fav_owner', on_delete=models.CASCADE)
  favorite = models.ForeignKey(User, related_name='fav_favorite', on_delete=models.CASCADE)
  priority = models.PositiveIntegerField(default=0)
  created_at = models.DateTimeField(auto_now_add=True)
  updated_at = models.DateTimeField(auto_now=True)

class Package(models.Model):

  STATUS_OPEN = 'open'
  STATUS_RESOLVED_RECIPIENT = 'resolve_recipient'
  STATUS_DELIVERED = 'delivered'
  STATUS_RETURNED = 'returned'
  STATUS_CHOICES = ((STATUS_OPEN, 'Open'), (STATUS_RESOLVED_RECIPIENT, 'Resolved recipient'), (STATUS_DELIVERED, 'delivered'), (STATUS_RETURNED, 'returned'))

  deliverer = models.ForeignKey(User, related_name='pac_deliverer', on_delete=models.CASCADE)
  purchaser = models.ForeignKey(User, related_name='pac_purchaser', on_delete=models.CASCADE)
  recipient = models.ForeignKey(User, related_name='pac_recipient', on_delete=models.CASCADE)
  status = models.CharField(max_length=31, choices=STATUS_CHOICES, default=STATUS_OPEN)
  created_at = models.DateTimeField(auto_now_add=True)
  updated_at = models.DateTimeField(auto_now=True)
