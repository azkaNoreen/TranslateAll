package azka.noreen.translateall;

public class LanguageModel {
    public String code;
    public String name;
    public String nativeName;
    public String country;

    public LanguageModel(String code, String name, String nativeName, String country) {
        this.code = code;
        this.name = name;
        this.nativeName = nativeName;
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
