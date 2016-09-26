from __future__ import unicode_literals

from rest_framework import viewsets
from rest_framework.response import Response
from rest_framework import status

import subprocess

import settings

actions = ['start', 'stop', 'restart']


class HadoopViewSet(viewsets.ViewSet):
    """
    API endpoint that controls the hadoop framework.
    """

    def list(self, request):
        action = request.GET.get('action')

        if action is not None and action in actions:
            hadoop_settings = settings.get_hadoop_settings(["paths", "actions"])
            action = hadoop_settings["paths"]["hadoop_home"] + hadoop_settings["paths"]["sbin"] + "/" + hadoop_settings["actions"][action]
            print action

            process = subprocess.Popen(action.split(), stdout=subprocess.PIPE)
        return Response({"output": stdout}status=status.HTTP_200_OK)
