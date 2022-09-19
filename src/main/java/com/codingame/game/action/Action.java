package com.codingame.game.action;

import java.util.Objects;
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

    public static Action fromInput(String input) throws InvalidAction {
        ActionBuilder builder = new ActionBuilder();
        String[] args = input.split(" ");

        if (args.length == 0) {
            throw new InvalidAction("Missing instruction");
        }

        MoveAction move = MoveAction.fromString(args[0])
                .orElseThrow(() -> new InvalidAction("Unknown action : " + args[0]));
        builder.moveAction(move);

        switch (move) {
            case WAIT:
            case TELEPORT:
                // empty
                break;
            case MOVE:
            case PUSH:
            case PULL:
                buildMove(builder, args);
                break;
            default:
                throw new InvalidAction("Unknown move : " + move);
        }
        return builder.build();
    }

    private static void buildMove(ActionBuilder builder, String[] args) throws InvalidAction {
        if (args.length == 1) {
            throw new InvalidAction("Missing instruction");
        }
        builder.direction(Direction.fromString(args[1])
                .orElseThrow(() -> new InvalidAction("Unknown direction : " + args[1])));
    }

    public String instruction() {
        String instruction = moveAction.toString();
        if (Objects.nonNull(direction)) {
            instruction += ' ' + direction.toString();
        }
        return instruction;
    }
}
