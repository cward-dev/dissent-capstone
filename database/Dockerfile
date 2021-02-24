FROM mysql:latest

COPY *.sql /docker-entrypoint-initdb.d/

ENV MYSQL_ROOT_PASSWORD=password
ENV MYSQL_DATABASE=dissent