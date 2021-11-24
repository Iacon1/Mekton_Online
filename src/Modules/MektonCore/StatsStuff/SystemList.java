// By Iacon1
// Created 11/23/2021
// A list of components (i. e. things with health)

package Modules.MektonCore.StatsStuff;

import Utils.GappyArrayList;
import Utils.GSONConfig.TransSerializables.SerializableDLListHolder;

public abstract class SystemList<T extends SystemList, C extends System<T>> extends SerializableDLListHolder<T, C, GappyArrayList<C>> {}
