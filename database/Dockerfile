FROM mysql:8

ENV MYSQL_ROOT_PASSWORD admin
ENV MYSQL_USER db
ENV MYSQL_PASSWORD db
ENV MYSQL_DATABASE shop

VOLUME /var/lib/mysql
ADD ./init.sql /docker-entrypoint-initdb.d/init.sql

EXPOSE 3306
