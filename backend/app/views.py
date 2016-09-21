from django.shortcuts import render
from __future__ import unicode_literals
from rest_framework import viewsets

import subprocess


class UserViewSet(viewsets.GenericViewSet):
    """
    API endpoint that controls the hadoop framework.
    """

    def list(self, request):
        start = request.GET.get('start')

        if start is not None:
            bashCommand = "cwm --rdf test.rdf --ntriples > test.nt"
process = subprocess.Popen(bashCommand.split(), stdout=subprocess.PIPE)
output = process.communicate()[0]



        return Response(status=status.HTTP_200_OK)
