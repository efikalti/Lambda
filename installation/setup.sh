#!/bin/bash

### FUNCTIONS ###

# Function that prints to stderr with red colour
echoerr() {
  echo -e "[ERROR] \e[01;31m$1\e[0m" >&2
}


# Function that prints warnings with yellow colour
echowarn() {
  echo -e "[WARN] \e[01;33m$1\e[0m"
}


# Function that prints information with green colour
echoinfo() {
  echo -e "[INFO] \e[32m$1 $2 $3 $4\e[0m"
}

# Function that prints with green colour
echo_green() {
  echo -e "\e[32m$1 $2 $3 $4\e[0m"
}

echo_packages(){
  hadoop=false
  elastic=false
  logstash=false
  supervisord=false

  echo
  echoinfo "The following packages are required from the Lambda project to run:"
  echo_green "1. Hadoop version 2.7 or greater."
  echo_green "2. Elasticsearch version 2.2 or greater."
  echo_green "3. Supervisord version 2.2 or greater."
  echo
  echoinfo "You can select from 0 (none) to 4 (all) packages to be installed."
  echoinfo "Type the numbers of the packages you want separated by spaces and press [ENTER]."
  read raw_packages

  packages=$(echo $raw_packages | tr " " "\n")

  for package in $packages
    do
      case $package in
        [1])
          hadoop=true
          ;;
        [2])
          elastic=true
          ;;
        [3])
          supervisord=true
          ;;
    esac
  done

  if ! $hadoop && ! $elastic && ! $logstash && ! $supervisord;
    then
    echoinfo "No package selected for installation, the process with terminate now."
    exit 0;
  fi

  cd "$(dirname "ansible")"
  if $hadoop;
    then run_ansible $hadoop
  fi


}

install_ansible(){
  echoinfo  "Installing Ansible...";
  echo
  if [ "$1" = "Ubuntu" ];
    then
        apt install -y ansible
    else if [ "$1" = "Centos" ];
      then
        yum install -y git
    fi
  fi
}

# Function to check if Git is present in the system and install it if not.
git_check() {

  # Check if git is installed in the system
  command -v git >/dev/null 2>&1 ||
  {
    # Try to install git if system is Ubuntu or Centos
    echowarn  "Git is required but it's not installed.";
    echoinfo  "Installing Git...";
    echo
    if [ "$1" = "Ubuntu" ];
      then
          apt-get install -y git
      else if [ "$1" = "Centos" ];
        then
          yum install -y git
      fi
    fi
    exit 1;
  }

  # Print git installed version
  OUT=$(command git --version 2>&1 | (head -n1 && tail -n1) | awk '{split($0, array, " "); print array[3]}')
  echoinfo "Git version installed " $OUT

  # Check if ansible is already present in /tmp/ folder from previous tries
  if [ ! -d "/tmp/ansible/" ]; then
    echoinfo  "Cloning ansible repository in /tmp/ ...";
    git clone git://github.com/ansible/ansible.git /tmp/ansible --recursive
  fi

    echoinfo  "Installing Ansible...";

  # Check that the folder cloned successfully and then run the install script env-setup
  if [ -d "/tmp/ansible/hacking/" ]; then
    source /tmp/ansible/hacking/env-setup
  else
    echoerr "Installation cannot locate ansible folder for installation."
    echoerr "Process will terminate now."
  fi
}


# Function to run the ansible script for cluster installation
run_ansible() {
  cd "ansible"
  # command="ansible-playbook playbooks/hadoop-install.yml --list-tasks"
  command="ansible-playbook playbooks/hadoop-install.yml"
  eval $command
  echo "finished"
  exit 0;
}


### MAIN ###

echoinfo  "Setup starting...";
echo

# Get the system specifications
echoinfo  "System specifications:";

OS=$(lsb_release -si)
ARCH=$(uname -m | sed 's/x86_//;s/i[3-6]86/32/')
VER=$(lsb_release -sr)

# Check if the variable OS was retrieved and set correctly
if [ -z "$OS" ];
  then
    echoerr "Error.Could not detect system specifications."
    echoerr "Installation will terminate now."
    exit 1;
fi

# Print the system specifications
echoinfo $OS $VER x$ARCH
echo

echoinfo  "The computer running the installation process needs password-less ssh access to the host machines.";
echoinfo  "Make sure you can ssh to the hosts and press [ENTER] to continue with the process or anything else to terminate it.";

read -s -n 1 key
if [[ $key = "" ]]; then
  echo
  echoinfo "Installation will continue now."
else
  echo
  echoinfo "Installation will terminate now."
  exit 1;
fi


# Check if ansible is installed in the system
command -v ansible >/dev/null 2>&1 ||
{
  # Try to install git if system is Ubuntu or Centos
  echowarn  "Ansible is required but it's not installed.";
  install_ansible $OS
}

# Print Ansible installed version
OUT=$(command ansible --version 2>&1 | (head -n1 && tail -n1) | awk '{split($0, array, " "); print array[2]}')
echoinfo "Ansible version installed " $OUT

# Print packages for installation
echo_packages
