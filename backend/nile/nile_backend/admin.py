from django.contrib import admin

from .models import *

admin.site.register(User)
admin.site.register(Address)
admin.site.register(Location)
admin.site.register(Package)
admin.site.register(Favorite)
