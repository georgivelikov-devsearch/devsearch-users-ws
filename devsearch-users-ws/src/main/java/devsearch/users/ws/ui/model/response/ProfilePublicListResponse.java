package devsearch.users.ws.ui.model.response;

import java.util.Collection;

public class ProfilePublicListResponse {

    private int currentPage;
    private int totalPages;
    private Collection<ProfilePublicResponse> profiles;

    public int getCurrentPage() {
	return currentPage;
    }

    public void setCurrentPage(int currentPage) {
	this.currentPage = currentPage;
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
