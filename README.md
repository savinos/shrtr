# shrtr

This is a simple Dropwizard Application implementing a URL shortening service. 

I maintain this mostly to experiment with a few technologies.

-- CONFIG --

> var/conf/shrtr.yml

prefix: $shrtr_host


-- API --

curl -XGET $hostname:$port/v0/shorten?url=https://www.google.com

	> https://$shrtr_host/50328aa4


curl -XGET $hostname:$port/v0/actual?url=https://$shrtr_host/50328aa4

	> https://www.google.com


