package com.ricardonavarrom.mercury.presentation.view;

import me.panavtec.threaddecoratedview.views.qualifiers.ThreadDecoratedView;

@ThreadDecoratedView public interface ArtistView {
    void showRank(int rank);
    void showName(String name);
    void showGenres(String genres);
    void showImage(String image);
}
