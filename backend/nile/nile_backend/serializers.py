from rest_framework import serializers
from nile_backend.models import *


class LocationSerializer(serializers.Serializer):	
    id = serializers.IntegerField(required=False)
    lat = serializers.DecimalField(max_digits=13, decimal_places=10)
    lng = serializers.DecimalField(max_digits=13, decimal_places=10)
    created_at = serializers.DateTimeField(required=False)
    updated_at = serializers.DateTimeField(required=False)

    def create(self, validated_data):
      return Location.objects.create(**validated_data)

class UserSerializer(serializers.Serializer):
    id = serializers.IntegerField(required=False)
    name = serializers.CharField(max_length=255)
    token = serializers.CharField(max_length=255, required=False)
    type = serializers.CharField(max_length=31)
    created_at = serializers.DateTimeField(required=False)
    updated_at = serializers.DateTimeField(required=False)

    def create(self, validated_data):
      return User.objects.create(**validated_data)

class AddressSerializer(serializers.Serializer):
    id = serializers.IntegerField(required=False)
    location = LocationSerializer()
    name = serializers.CharField(max_length=255)
    street = serializers.CharField(max_length=255)
    postcode = serializers.CharField(max_length=31)
    city = serializers.CharField(max_length=255)
    country = serializers.CharField(max_length=255)
    floor = serializers.IntegerField()
    created_at = serializers.DateTimeField(required=False)
    updated_at = serializers.DateTimeField(required=False)

    def create(self, validated_data):
      location_data = validated_data.pop('location', None)
      if location_data:
        location = Location.objects.get_or_create(**location_data)[0]
      validated_data['location'] = location
      return Address.objects.create(**validated_data)

class FavoriteSerializer(serializers.Serializer):
    id = serializers.IntegerField(required=False)
    owner = UserSerializer()
    favorite = UserSerializer()
    priority = serializers.IntegerField()
    created_at = serializers.DateTimeField(required=False)
    updated_at = serializers.DateTimeField(required=False)

    def create(self, validated_data):
      return Favorite.objects.create(**validated_data)

class PackageSerializer(serializers.Serializer):
    id = serializers.IntegerField(required=False)
    deliverer = UserSerializer()
    purchaser = UserSerializer()
    recipient = UserSerializer(required=False)
    status = serializers.CharField(max_length=31, required=False)
    created_at = serializers.DateTimeField(required=False)
    updated_at = serializers.DateTimeField(required=False)
    mins_until_delivery = serializers.FloatField(max_value=None, min_value=None, required=False, allow_null=True)
    sender = serializers.CharField(max_length=255, required=False)

    def create(self, validated_data):
      return Package.objects.create(**validated_data)
