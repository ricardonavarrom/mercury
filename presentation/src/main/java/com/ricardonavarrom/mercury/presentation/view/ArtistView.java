package com.ricardonavarrom.mercury.presentation.view;

import me.panavtec.threaddecoratedview.views.qualifiers.ThreadDecoratedView;

@ThreadDecoratedView public interface ArtistView {
    void showRank(int rank);
    void showRankGenre(String rankGenre);
    void showRankDate(String rankDateString);
    void showName(String name);
    void showGenres(String genres);
    void showImage(String image);
}
