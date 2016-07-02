"""nile URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.9/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url, include
from django.contrib import admin
from rest_framework import routers, serializers, viewsets
from rest_framework.parsers import JSONParser
from nile_backend.models import Location, User, Address, Favorite, Package
from rest_framework.urlpatterns import format_suffix_patterns
from nile_backend import views


urlpatterns = [
    url(r'^admin/', admin.site.urls),
    url(r'^api-auth/', include('rest_framework.urls', namespace='rest_framework')),
    url(r'^users/$', views.UserList.as_view()),
]

urlpatterns = format_suffix_patterns(urlpatterns)

# class PackageSerializer(serializers.Serializer):
#    id = serializers.IntegerField(readOnly=true)
    # deliverer = serializers.
#    name = serializers.CharField(max_length=200)
