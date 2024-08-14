package brockresource.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Search {

    @NotEmpty
    @Size(min = 1, max = 15, message = "{validation.text.size}")
    private String searchStr;

    public Search() {
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    public String getSearchStr() {
        return searchStr;
    }
}
