package com.nguyennk.newsdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "Articles")
public class Article extends Model implements Parcelable {
    @Column
    private String webUrl;
    @Column
    private String snippet;
    @Column
    private String source;
    @Column
    private Date pubDate;
    @Column
    private String sectionName;
    @Column(name="headline", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    private Headline headline;
    @Column(name="multimedia", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    private List<Multimedia> multimedia = new ArrayList<Multimedia>();

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<Multimedia> multimedia) {
        this.multimedia = multimedia;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public Article() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.webUrl);
        dest.writeString(this.snippet);
        dest.writeString(this.source);
        dest.writeLong(pubDate != null ? pubDate.getTime() : -1);
        dest.writeString(this.sectionName);
        dest.writeParcelable(this.headline, 0);
        dest.writeTypedList(multimedia);
    }

    protected Article(Parcel in) {
        this.webUrl = in.readString();
        this.snippet = in.readString();
        this.source = in.readString();
        long tmpPubDate = in.readLong();
        this.pubDate = tmpPubDate == -1 ? null : new Date(tmpPubDate);
        this.sectionName = in.readString();
        this.headline = in.readParcelable(Headline.class.getClassLoader());
        this.multimedia = in.createTypedArrayList(Multimedia.CREATOR);
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public static class Multimedia implements Parcelable {
        private Integer width;
        private String url;
        private Integer height;
        private String subtype;
        private String type;

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.width);
            dest.writeString(this.url);
            dest.writeValue(this.height);
            dest.writeString(this.subtype);
            dest.writeString(this.type);
        }

        public Multimedia() {
        }

        protected Multimedia(Parcel in) {
            this.width = (Integer) in.readValue(Integer.class.getClassLoader());
            this.url = in.readString();
            this.height = (Integer) in.readValue(Integer.class.getClassLoader());
            this.subtype = in.readString();
            this.type = in.readString();
        }

        public static final Creator<Multimedia> CREATOR = new Creator<Multimedia>() {
            public Multimedia createFromParcel(Parcel source) {
                return new Multimedia(source);
            }

            public Multimedia[] newArray(int size) {
                return new Multimedia[size];
            }
        };
    }

    public static class Headline implements Parcelable {
        private String main;

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.main);
        }

        public Headline() {
        }

        protected Headline(Parcel in) {
            this.main = in.readString();
        }

        public static final Creator<Headline> CREATOR = new Creator<Headline>() {
            public Headline createFromParcel(Parcel source) {
                return new Headline(source);
            }

            public Headline[] newArray(int size) {
                return new Headline[size];
            }
        };
    }
}
