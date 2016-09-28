"""
This file includes all the custom made exceptions. These exceptions are thrown by the API and
used to override the structure of the response messages.
"""

from rest_framework import status
from rest_framework.response import Response
from rest_framework.views import exception_handler
from rest_framework.exceptions import APIException


class CustomAuthenticationFailed(APIException):
    status_code = status.HTTP_401_UNAUTHORIZED
    default_detail = "Unauthorized. Request failed because of invalid password."


class CustomRequestFailed(APIException):
    status_code = status.HTTP_400_BAD_REQUEST
    default_detail = "Bad request."


def parse_custom_exception(exception, default_response):
    response = dict({'errors': []})

    for key, value in default_response.data.items():
        response['errors'].append({'status': default_response.status_code,
                                   'detail': value})

    response_status = default_response.status_code
    return Response(response, response_status)


def custom_exception_handler(exc, context):

    default_response = exception_handler(exc, context)

    if isinstance(exc, custom_exceptions):
        return parse_custom_exception(exc, default_response)

    return default_response
