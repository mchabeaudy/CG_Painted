package com.codingame.game.action;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class Action {

    private final MoveAction moveAction;
    private Direction direction;
    private String message;

    public static Action fromInput(String input) throws InvalidAction {
        ActionBuilder builder = new ActionBuilder();
        String[] args = input.split(" ");

        if (args.length == 0) {
            throw new InvalidAction("Missing instruction");
        }

        MoveAction move = MoveAction.fromString(args[0])
                .orElseThrow(() -> new InvalidAction("Unknown action : " + args[0]));
        builder.moveAction(move);
        builder.message(input);

        switch (move) {
            case WAIT:
                // empty
                break;
            case MOVE:
            case PUSH:
            case PULL:
                buildMove(builder, args);
                break;
            case TAKE:
                buildTake(builder, args);
                break;
            default:
                throw new InvalidAction("Unknown move : " + move);
        }
        return builder.build();
    }

    private static void buildTake(ActionBuilder builder, String[] args) throws InvalidAction {
         if (args.length > 1) {
            builder.message(args[1]);
        }
    }

    private static void buildMove(ActionBuilder builder, String[] args) throws InvalidAction {
        if (args.length == 1) {
            throw new InvalidAction("Missing instruction");
        } else if (args.length > 2) {
            builder.message(args[2]);
        }
        builder.direction(Direction.fromString(args[1])
                .orElseThrow(() -> new InvalidAction("Unknown direction : " + args[1])));
    }

}
