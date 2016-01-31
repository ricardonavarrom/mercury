package com.ricardonavarrom.mercury.presentation.view;

import com.ricardonavarrom.mercury.domain.model.Artist;
import java.util.List;
import me.panavtec.threaddecoratedview.views.qualifiers.ThreadDecoratedView;

@ThreadDecoratedView public interface ArtistsView {
    void setArtists(List<Artist> artists);
    void showLoading();
    void hideLoading();
    void showRefreshButton();
    void hideRefreshButton();
    void showLoadArtistsError();
    void showNetworkError();
}
