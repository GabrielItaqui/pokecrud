package com.TrainderCRUD.controller;

import com.TrainerCRUD.model.Trainer;
import com.TrainerCRUD.service.TrainerService;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class TrainerServiceTest {

    @Test
    void testCreateTrainer() {
        TrainerService service = new TrainerService();
        Trainer trainer = new Trainer();
        trainer.setNickname("Ash");

        Trainer createdTrainer = service.create(trainer);

        assertNotNull(createdTrainer);
        assertEquals(trainer.getNickname(), createdTrainer.getNickname());
    }

    @Test
    void testReadTrainer() {
        TrainerService service = new TrainerService();
        Trainer trainer = new Trainer();
        trainer.setId(1);
        trainer.setNickname("Ash");

        service.create(trainer);

        Trainer foundTrainer = service.read(1L);

        assertNotNull(foundTrainer);
        assertEquals(trainer.getId(), foundTrainer.getId());
        assertEquals(trainer.getNickname(), foundTrainer.getNickname());

        foundTrainer = service.read(2L);

        assertNull(foundTrainer);
    }

    @Test
    public void testReadAllTrainers() {
        TrainerService service = new TrainerService();

        Trainer trainer1 = new Trainer();
        trainer1.setId(1);
        trainer1.setNickname("Ash");

        Trainer trainer2 = new Trainer();
        trainer2.setId(2);
        trainer2.setNickname("Misty");

        service.create(trainer1);
        service.create(trainer2);

        List<Trainer> trainers = service.readAll();

        assertEquals(2, trainers.size());
        assertEquals(trainer1.getId(), trainers.get(0).getId());
        assertEquals(trainer1.getNickname(), trainers.get(0).getNickname());
        assertEquals(trainer2.getId(), trainers.get(1).getId());
        assertEquals(trainer2.getNickname(), trainers.get(1).getNickname());
    }

    @Test
    public void testUpdateTrainer() {
        TrainerService service = new TrainerService();

        Trainer trainer = new Trainer();
        trainer.setId(1);
        trainer.setNickname("Ash");

        service.create(trainer);

        trainer.setNickname("Red");

        Trainer updatedTrainer = service.update(trainer);

        assertNotNull(updatedTrainer);
        assertEquals(trainer.getId(), updatedTrainer.getId());
        assertEquals(trainer.getNickname(), updatedTrainer.getNickname());

        updatedTrainer = service.read(1L);

        assertNotNull(updatedTrainer);
        assertEquals(trainer.getId(), updatedTrainer.getId());
        assertEquals(trainer.getNickname(), updatedTrainer.getNickname());
    }

    @Test
    public void testDeleteTrainer() {
        TrainerService service = new TrainerService();

        Trainer trainer = new Trainer();
        trainer.setId(1);
        trainer.setNickname("Ash");

        service.create(trainer);

        service.delete(1L);

        Trainer foundTrainer = service.read(1L);

        assertNull(foundTrainer);
    }
}
