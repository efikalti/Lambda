from functions import check_hadoop_services
from .exceptions import CustomRequestFailed
from rest_framework import permissions

actions= ['start', 'stop', 'restart', 'upload']

class HadoopPermission(permissions.BasePermission):
    """
    Validity check for hadoop actions.
    """

    def has_permission(self, request, view):
        # Check validity of action parameter
        action = request.GET.get('action')
        if action is None:
            raise CustomRequestFailed('No action parameter provided.')
        if action not in actions:
            keys = ",".join(actions["actions"].keys())
            message = "The provided action " + action + ", is not in the list of accepted actions: [" + keys + "]."
            raise CustomRequestFailed(message)
        running = check_hadoop_services()
        if action is not "stop" and not running:
            raise CustomRequestFailed("Not all of the hadoop services are running.Start them and try again.")

        # Check validity of request parameters per request method
        method = request.META['REQUEST_METHOD']
        if method == 'GET':
            pass

        return True
