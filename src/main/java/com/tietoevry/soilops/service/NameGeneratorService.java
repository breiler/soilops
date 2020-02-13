package com.tietoevry.soilops.service;

    import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class NameGeneratorService {
    private List<String> names = Arrays.asList("Balsam", "Basil", "Bay", "Bentley", "Bramble", "Bryony", "Calla", "Lily", "Cerise", "Clove", "Elowen", "Fleur", "Forsythia", "Genista", "Hazel", "Hawthorne", "Heath", "Iris", "Ivy", "Indigo", "Jessamine", "Juniper", "Kale", "Layton", "Madara", "Marwa", "Moss", "Oakley", "Orrin", "Pepper", "Ponga", "Primrose", "Rowan", "Sage", "Saffron", "Sorrel", "Terra", "Vernon", "Violet", "Wilder", "Yarrow", "Zinnia");

    public String generate() {
        return names.get(RandomUtils.nextInt(0, names.size())) + " " + names.get(RandomUtils.nextInt(0, names.size()));
    }
}
