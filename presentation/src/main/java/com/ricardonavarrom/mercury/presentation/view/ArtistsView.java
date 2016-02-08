package com.ricardonavarrom.mercury.presentation.view;

import com.ricardonavarrom.mercury.domain.model.Artist;
import java.util.List;
import me.panavtec.threaddecoratedview.views.qualifiers.ThreadDecoratedView;

@ThreadDecoratedView public interface ArtistsView {
    void setArtists(List<Artist> artists);
    void showRecyclerView();
    void hideRecyclerView();
    void showLoading();
    void hideLoading();
    void showRefreshRankingView();
    void hideRefreshRankingView();
    void showLoadArtistsError();
    void showNetworkError();
    void saveArtistsRankingExpirationDate(String artistRankingExpirationDateString);
}
