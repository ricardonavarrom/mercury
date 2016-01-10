package com.ricardonavarrom.mercury.presentation;

public interface MercuryViewInjector {
    <V> V inject(V view);
    <V> V nullObjectPatternView(V view);
}
