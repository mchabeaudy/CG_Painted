package com.codingame.game.action;

import com.codingame.game.map.Point;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Action {

    private static final String POSITIVE_INTEGER_REGEX = "^\\d+$";

    private final MoveAction moveAction;
    private Direction direction;
    private Point target;

    public static Action fromInput(String input) throws InvalidAction {
        ActionBuilder builder = new ActionBuilder();
        if (Objects.isNull(input) || input.length() == 0) {
            throw new InvalidAction("Missing instruction");
        }
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
                buildMove(builder, args);
                break;
            case PUSH:
            case PULL:
                buildPushPull(builder, args);
                break;
            default:
                throw new InvalidAction("Unknown move : " + move);
        }
        return builder.build();
    }

    private static void buildMove(ActionBuilder builder, String[] args) throws InvalidAction {
        if (args.length < 2) {
            throw new InvalidAction("Missing instruction");
        }
        Optional<Direction> direction = Direction.fromString(args[1]);
        if (direction.isPresent()) {
            builder.direction(direction.get());
            builder.target(null);
        } else {
            if (args.length < 3) {
                throw new InvalidAction("Missing instruction");
            }
            if (!args[1].matches(POSITIVE_INTEGER_REGEX) || !args[2].matches(POSITIVE_INTEGER_REGEX)) {
                throw new InvalidAction("Error parsing coordinate. Make sure 2nd and 3rd argument are integers");
            }
            builder.target(Point.of(Integer.parseInt(args[1]), Integer.parseInt(args[2])));
            builder.direction(null);
        }
    }

    private static void buildPushPull(ActionBuilder builder, String[] args) throws InvalidAction {
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
