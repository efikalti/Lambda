from exceptions import CustomRequestFailed
from rest_framework import viewsets
from rest_framework.response import Response
from rest_framework import status

from permissions import *
from functions import run_command, check_local_path, check_hadoop_services, check_hadoop_applications
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

        if action == 'check':
            running, stopped = check_hadoop_services(True)
            num, output = check_hadoop_applications()
            return Response({"running_services": running, "stopped_services": stopped, "number_of_running_applications": num, "running_applications": output}, status=status.HTTP_200_OK)
        if action == 'restart':
            restart = hadoop_settings["paths"]["hadoop_home"] + hadoop_settings["paths"]["sbin"] + "/" + hadoop_settings["actions"]['stop']
            output = run_command(restart, True)
            action = 'start'

        action = hadoop_settings["paths"]["hadoop_home"] + hadoop_settings["paths"]["sbin"] + "/" + hadoop_settings["actions"][action]
        output = run_command(action, True)

        return Response({"output": output}, status=status.HTTP_200_OK)


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
            args = request.data.get("args")
            # Try to run
            try:
                action = hadoop_settings["paths"]["hadoop_home"] + hadoop_settings["paths"]["bin"] + "/hadoop " + hadoop_settings["actions"][action] + " " + path + " " + args
                run_command(action)
            except:
                raise CustomRequestFailed("Something went wrong while trying to run the job.")
        return Response(status=status.HTTP_200_OK)
