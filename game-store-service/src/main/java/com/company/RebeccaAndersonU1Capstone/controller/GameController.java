package com.company.RebeccaAndersonU1Capstone.controller;

import com.company.RebeccaAndersonU1Capstone.service.GameService;
import com.company.RebeccaAndersonU1Capstone.viewModel.GameViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    GameService gameService;

    // Create
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameViewModel createGame(@RequestBody @Valid GameViewModel gvm) {
        return gameService.saveGame(gvm);
    }

    // Get one
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GameViewModel getOneGame(@PathVariable("id") int gameId) {
        return gameService.getGameById(gameId);
    }

    // Get all
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GameViewModel> getAllGame() {
        return gameService.getAllGames();
    }

    // Get by studio
    @GetMapping("/studio/{studio}")
    @ResponseStatus(HttpStatus.OK)
    public List<GameViewModel> getByStudio(@PathVariable("studio") String studio) {
        return gameService.getGameByStudio(studio);
    }

    // Get by esrb
    @GetMapping("/esrb/{rating}")
    @ResponseStatus(HttpStatus.OK)
    public List<GameViewModel> getByRating(@PathVariable("rating") String rating) {
        return gameService.getGameByRating(rating);
    }

    // get by title
    @GetMapping("/title/{title}")
    @ResponseStatus(HttpStatus.OK)
    public List<GameViewModel> getByTitle(@PathVariable("title") String title) {
        return gameService.getGameByTitle(title);
    }

    // update
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GameViewModel updateGame(@PathVariable("id") int gameId, @RequestBody @Valid GameViewModel gvm) {
        if (gvm.getId() == 0)
            gvm.setId(gameId);
        if (gameId != gvm.getId()) {
            throw new IllegalArgumentException("Game ID on path must match the ID in Game object");
        }
        return gameService.updateGame(gvm);
    }

    // delete
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGame(@PathVariable("id") int gameId) {

        gameService.removeGame(gameId);
    }
}
