package it.mycraft.powerlib.common.objects;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pair<L, R> {

    private L left;
    private R right;

}
