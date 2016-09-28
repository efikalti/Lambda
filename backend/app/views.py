from exceptions import CustomRequestFailed
from rest_framework import viewsets
from rest_framework.response import Response
from rest_framework import status

from permissions import *
from functions import run_command, check_local_path
import settings
import os


class HadoopViewSet(viewsets.ViewSet):
    """
    API endpoint that controls the hadoop framework.
    """
    permission_classes = (HadoopPermission,)

    def list(self, request):
        action = request.GET.get('action')
        hadoop_settings = settings.get_hadoop_settings(["paths", "actions"])

        if action == 'restart':
            restart = hadoop_settings["paths"]["hadoop_home"] + hadoop_settings["paths"]["sbin"] + "/" + hadoop_settings["actions"]['stop']
            run_command(restart)
            action = 'start'

        action = hadoop_settings["paths"]["hadoop_home"] + hadoop_settings["paths"]["sbin"] + "/" + hadoop_settings["actions"][action]
        run_command(action)

        return Response(status=status.HTTP_200_OK)


    def create(self, request):
        action = request.GET.get('action')
        hadoop_settings = settings.get_hadoop_settings(["paths", "actions"])

        if action == 'upload':
            path = request.data.get("path")
            dest = request.data.get("dest")
            # Try to upload
            try:
                action = hadoop_settings["paths"]["hadoop_home"] + hadoop_settings["paths"]["bin"] + "/hadoop " + hadoop_settings["actions"][action] + " " + path + " " + dest
                run_command(action)
            except:
                raise CustomRequestFailed("Something went wrong while trying to upload.")
        elif action == 'run':
            path = request.data.get("path")

        return Response(status=status.HTTP_200_OK)
