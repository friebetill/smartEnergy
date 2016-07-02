from rest_framework import serializers

class LocationSerializer(serializers.Serializer):	
    id = serializers.IntegerField()
    lat = serializers.DecimalField(max_digits=13, decimal_places=10)
    lng = serializers.DecimalField(max_digits=13, decimal_places=10)
    created_at = serializers.DateTimeField()
    updated_at = serializers.DateTimeField()

class UserSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    name = serializers.CharField(max_length=255)
    created_at = serializers.DateTimeField()
    updated_at = serializers.DateTimeField()

class PackageSerializer(serializers.Serializer):
    id = serializers.IntegerField()
    deliverer = UserSerializer()
    purchaser = UserSerializer()
    recipient = UserSerializer()
    status = serializers.CharField(max_length=31)
    created_at = serializers.DateTimeField()
    updated_at = serializers.DateTimeField()


