package edu.bsu.games.guesser.config;

import edu.bsu.games.guesser.data.storage.DataStorage;
import edu.bsu.games.guesser.data.storage.Game;
import edu.bsu.games.guesser.data.storage.Genre;
import edu.bsu.games.guesser.utils.FileUtils;
import java.util.Comparator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * created by @Kabaye
 * date 29.11.2020
 */

@Slf4j
@Configuration
@RequiredArgsConstructor
public class GameConfiguration {

    @Bean
    public DataStorage dataStorage() {
        return FileUtils.parseJson(FileUtils.getFileContent("data.json"), DataStorage.class);
    }
}
