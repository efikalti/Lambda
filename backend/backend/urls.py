"""
backend URL Configuration
"""

from django.conf.urls import include, url
from django.contrib import admin
from rest_framework import routers
from app import views


router = routers.DefaultRouter()
router.register(r'api/hadoop/?', views.HadoopViewSet)


urlpatterns = [
    url(r'^admin/', include(admin.site.urls)),
    url(r'^', include(router.urls)),
]
