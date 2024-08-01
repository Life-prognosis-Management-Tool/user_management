#! /bin/bash

function register_patient(){
	regexPattern="^(([-a-zA-Z0-9\!#\$%\&\'*+/=?^_\`{\|}~]+|(\"([][,:;<>\&@a-zA-Z0-9\!#\$%\&\'*+/=?^_\`{\|}~-]|(\\\\[\\ \"]))+\"))\.)*([-a-zA-Z0-9\!#\$%\&\'*+/=?^_\`{\|}~]+|(\"([][,:;<>\&@a-zA-Z0-9\!#\$%\&\'*+/=?^_\`{\|}~-]|(\\\\[\\ \"]))+\"))@\w((-|\w)*\w)*\.(\w((-|\w)*\w)*\.)*\w{2,4}$"
	echo "Enter your email:"
	read $email

	# Check if the email is valid
	if [[ $email =~ $regexPattern ]]; then
		echo "The email is valid"
	else
		echo "Invalid email"
	fi

	echo "The UUID of ${email} is $(uuidgen) "
}

register_patient
