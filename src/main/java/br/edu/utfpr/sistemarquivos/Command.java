package br.edu.utfpr.sistemarquivos;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;

public enum Command {

    LIST() {
        @Override
        boolean accept(String command) {
            final var commands = command.split(" ");
            return commands.length > 0 && commands[0].startsWith("LIST") || commands[0].startsWith("list");
        }

        @Override
        Path execute(Path path) throws IOException {

            // TODO implementar conforme enunciado
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
                for (Path entry : directoryStream) {
                    System.out.println(entry.getFileName());
                }
            }

            return path;
        }
    },
    SHOW() {
        private String[] parameters = new String[]{};

        @Override
        void setParameters(String[] parameters) {
            this.parameters = parameters;
        }

        @Override
        boolean accept(String command) {
            final var commands = command.split(" ");
            return commands.length > 0 && commands[0].startsWith("SHOW") || commands[0].startsWith("show");
        }

        @Override
        Path execute(Path path) {

            // TODO implementar conforme enunciado
            Path filePath = path.resolve(parameters[1]);

            if (!Files.isRegularFile(filePath)) {
                throw new UnsupportedOperationException("The specified path is not a file");
            }

            FileReader fileReader = new FileReader();
            fileReader.read(filePath);

            return path;
        }
    },
    BACK() {
        @Override
        boolean accept(String command) {
            final var commands = command.split(" ");
            return commands.length > 0 && commands[0].startsWith("BACK") || commands[0].startsWith("back");
        }

        @Override
        Path execute(Path path) {

            // TODO implementar conforme enunciado
            if (path.getParent() != null && !path.equals(Paths.get(Application.ROOT))) {
                return path.getParent();
            }

            return path;
        }
    },
    OPEN() {
        private String[] parameters = new String[]{};

        @Override
        void setParameters(String[] parameters) {
            this.parameters = parameters;
        }

        @Override
        boolean accept(String command) {
            final var commands = command.split(" ");
            return commands.length > 0 && commands[0].startsWith("OPEN") || commands[0].startsWith("open");
        }

        @Override
        Path execute(Path path) {

            // TODO implementar conforme enunciado
            Path newPath = path.resolve(parameters[1]);

            if (!Files.isDirectory(newPath)) {
                throw new UnsupportedOperationException("The specified path is not a directory.");
            }

            return newPath;
        }
    },
    DETAIL() {
        private String[] parameters = new String[]{};

        @Override
        void setParameters(String[] parameters) {
            this.parameters = parameters;
        }

        @Override
        boolean accept(String command) {
            final var commands = command.split(" ");
            return commands.length > 0 && commands[0].startsWith("DETAIL") || commands[0].startsWith("detail");
        }

        @Override
        Path execute(Path path) {
            // TODO implementar conforme enunciado
            Path targetPath = path.resolve(parameters[1]);

            if (Files.exists(targetPath)) {
                try {
                    BasicFileAttributeView attributeView = Files.getFileAttributeView(targetPath, BasicFileAttributeView.class);
                    BasicFileAttributes attributes = attributeView.readAttributes();

                    System.out.println("Details for: " + targetPath.getFileName());
                    System.out.println("Size: " + attributes.size() + " bytes");
                    System.out.println("Creation Time: " + attributes.creationTime());
                    System.out.println("Last Modified Time: " + attributes.lastModifiedTime());
                    System.out.println("Last Accessed Time: " + attributes.lastAccessTime());
                    System.out.println("Is Directory: " + attributes.isDirectory());
                    System.out.println("Is Regular File: " + attributes.isRegularFile());

                } catch (IOException e) {
                    throw new UnsupportedOperationException(e.getMessage());
                }
            } else {
                throw new UnsupportedOperationException("The specified path is not exist.");
            }

            return path;
        }
    },
    EXIT() {
        @Override
        boolean accept(String command) {
            final var commands = command.split(" ");
            return commands.length > 0 && commands[0].startsWith("EXIT") || commands[0].startsWith("exit");
        }

        @Override
        Path execute(Path path) {
            System.out.print("Saindo...");
            return path;
        }

        @Override
        boolean shouldStop() {
            return true;
        }
    };

    abstract Path execute(Path path) throws IOException;

    abstract boolean accept(String command);

    void setParameters(String[] parameters) {
    }

    boolean shouldStop() {
        return false;
    }

    public static Command parseCommand(String commandToParse) {

        if (commandToParse.isBlank()) {
            throw new UnsupportedOperationException("Type something...");
        }

        final var possibleCommands = values();

        for (Command possibleCommand : possibleCommands) {
            if (possibleCommand.accept(commandToParse)) {
                possibleCommand.setParameters(commandToParse.split(" "));
                return possibleCommand;
            }
        }

        throw new UnsupportedOperationException("Can't parse command [%s]".formatted(commandToParse));
    }
}
