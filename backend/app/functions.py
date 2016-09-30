import subprocess
import os

services = ['NodeManager', 'DataNode', 'NameNode', 'ResourceManager', 'SecondaryNameNode']


def run_command(command=None, return_out=False):
    process = subprocess.Popen(command.split(), stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    if return_out:
        return process.communicate()[0]
    print process.communicate()[0]


def check_hadoop_services(complete_output=False):
    output = run_command("jps", True)
    all_running = True
    if complete_output:
        running_services = list()
        stopped_services = list()
        for service in services:
            if service in output:
                running_services.append(service)
            else:
                stopped_services.append(service)
        return running_services, stopped_services
    else:
        for service in services:
            if service not in output:
                all_running = False
        return all_running


def check_local_path(path):
    if not os.path.exists(path):
        raise CustomRequestFailed("The prodided path does not exist.")
    elif not os.access(os.path.dirname(path), os.R_OK):
        raise CustomRequestFailed("This user does not have read permissions for this file.")
