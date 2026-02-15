FROM quay.io/keycloak/keycloak:24.0

# Configure a database vendor
ENV KC_DB=mysql

WORKDIR /opt/keycloak
# for demonstration purposes only, please make sure to use proper certificates in production instead
RUN /opt/keycloak/bin/kc.sh build

USER 1000

ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]
