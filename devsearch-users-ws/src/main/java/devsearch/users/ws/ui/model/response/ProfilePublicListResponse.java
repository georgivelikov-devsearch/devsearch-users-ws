package devsearch.users.ws.ui.model.response;

import java.util.Collection;

public class ProfilePublicListResponse {

    private int page;
    private int totalPages;
    private Collection<ProfilePublicResponse> profiles;

    public int getPage() {
	return page;
    }

    public void setPage(int page) {
	this.page = page;
    }

    public int getTotalPages() {
	return totalPages;
    }

    public void setTotalPages(int totalPages) {
	this.totalPages = totalPages;
    }

    public Collection<ProfilePublicResponse> getProfiles() {
	return profiles;
    }

    public void setProfiles(Collection<ProfilePublicResponse> profiles) {
	this.profiles = profiles;
    }
}
