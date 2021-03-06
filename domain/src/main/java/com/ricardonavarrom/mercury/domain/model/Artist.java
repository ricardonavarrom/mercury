package com.ricardonavarrom.mercury.domain.model;

public class Artist {

    private int id;
    private String name;
    private int rank;
    private String url;
    private String uri;
    private String genres;
    private String smallImage;
    private String mediumImage;
    private String bigImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getMediumImage() {
        return mediumImage;
    }

    public void setMediumImage(String mediumImage) {
        this.mediumImage = mediumImage;
    }

    public String getBigImage() {
        return bigImage;
    }

    public void setBigImage(String bigImage) {
        this.bigImage = bigImage;
    }

    @Override
    public String toString() {
        return name;
    }

    public Artist(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.rank = builder.rank;
        this.url = builder.url;
        this.uri = builder.uri;
        this.genres = builder.genres;
        this.smallImage = builder.smallImage;
        this.mediumImage = builder.mediumImage;
        this.bigImage = builder.bigImage;
    }

    public static class Builder {
        private int id;
        private String name;
        private int rank;
        private String url;
        private String uri;
        private String genres;
        private String smallImage;
        private String mediumImage;
        private String bigImage;

        public Builder id(int id) {
            this.id = id;

            return this;
        }

        public Builder name(String name) {
            this.name = name;

            return this;
        }

        public Builder rank(int rank)
        {
            this.rank = rank;

            return this;
        }

        public Builder url(String url)
        {
            this.url = url;

            return this;
        }

        public Builder uri(String uri)
        {
            this.uri = uri;

            return this;
        }

        public Builder genres(String genres)
        {
            this.genres = genres;

            return this;
        }

        public Builder smallImage(String smallImage)
        {
            this.smallImage = smallImage;

            return this;
        }

        public Builder mediumImage(String mediumImage)
        {
            this.mediumImage = mediumImage;

            return this;
        }

        public Builder bigImage(String bigImage)
        {
            this.bigImage = bigImage;

            return this;
        }

        public Artist build() {
            return new Artist(this);
        }
    }


}

