FROM openjdk:8-jre-alpine

ADD shrtr.tar /opt/services/

EXPOSE 8080
WORKDIR /opt/services/shrtr

CMD ["bin/shrtr", "server", "var/conf/shrtr.yml"]