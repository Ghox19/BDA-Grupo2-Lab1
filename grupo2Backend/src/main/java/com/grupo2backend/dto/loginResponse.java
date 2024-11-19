package com.grupo2backend.dto;

public class loginResponse {
    private String token;

    private int idUser;

    private loginResponse(Builder builder) {
        this.token = builder.token;
        this.idUser = builder.idUser;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String token;
        private int idUser;

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder idUser(int idUser) {
            this.idUser = idUser;
            return this;
        }

        public loginResponse build() {
            return new loginResponse(this);
        }
    }


    public String getToken() {
        return token;
    }

    public int getIdUser() {
        return idUser;
    }


    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    public void setToken(String token){ this.token = token; }
}
