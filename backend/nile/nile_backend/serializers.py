from rest_framework import serializers


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


