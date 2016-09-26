from __future__ import unicode_literals

from rest_framework import viewsets
from rest_framework.response import Response
from rest_framework import status

import subprocess

import settings

actions = ['start', 'stop', 'restart']
services = ['NodeManager', 'DataNode', 'NameNode', 'ResourceManager', 'SecondaryNameNode']


def run_command(command=None, return_out=False):
    process = subprocess.Popen(command.split(), stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    if return_out:
        return process.communicate()[0]
    print process.communicate()[0]


def check_hadoop_services():
    output = run_command("jps", True)
    all_running = True
    for service in services:
        if service not in output:
            all_running = False
    return all_running


class HadoopViewSet(viewsets.ViewSet):
    """
    API endpoint that controls the hadoop framework.
    """

    def list(self, request):
        action = request.GET.get('action')

        if action is not None and action in actions:
            hadoop_settings = settings.get_hadoop_settings(["paths", "actions"])
            running = check_hadoop_services()

            if action == 'start' and running:
                return Response({"status": "all services already running.Run action restart to restart them."}, status=status.HTTP_200_OK)
            elif action == 'restart':
                restart = hadoop_settings["paths"]["hadoop_home"] + hadoop_settings["paths"]["sbin"] + "/" + hadoop_settings["actions"]['stop']
                run_command(restart)
                action = 'start'

            action = hadoop_settings["paths"]["hadoop_home"] + hadoop_settings["paths"]["sbin"] + "/" + hadoop_settings["actions"][action]
            run_command(action)
            return Response(status=status.HTTP_200_OK)

        check_hadoop_services()
        return Response(status=status.HTTP_200_OK)
