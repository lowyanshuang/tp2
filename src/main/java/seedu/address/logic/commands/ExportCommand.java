package seedu.address.logic.commands;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;

import static java.util.Objects.requireNonNull;

public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_SUCCESS = "Data exported successfully.";
    public static final String MESSAGE_EMPTY = "No person recorded in the system.";

    private final Path rootPath = Paths.get("").toAbsolutePath();
    private final Path txtStoragePath = Paths.get(rootPath + "/data");

    private static ObservableList<Person> PERSON_LIST;
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public ExportCommand() {}

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        this.PERSON_LIST = model.getAddressBook().getPersonList();

        String inforToTxt = "";

        for(int i=0; i<PERSON_LIST.size(); i++) {
            String UserInfor = "Name: " + PERSON_LIST.get(i).getName() + "\n"
                             + "Phone: " + PERSON_LIST.get(i).getPhone() + "\n"
                             + "Email: " + PERSON_LIST.get(i).getEmail() + "\n"
                             + "Address: " + PERSON_LIST.get(i).getAddress() + "\n"
                             + "Remark: " + PERSON_LIST.get(i).getRemark() + "\n"
                             + "Tag(s): " + PERSON_LIST.get(i).getTags().toString()
                             + "\n\n";

            inforToTxt += UserInfor;
        }

        if(inforToTxt.equals(""))  return new CommandResult(MESSAGE_EMPTY);

        StorageManager.exportAddressBookToTxt(inforToTxt, txtStoragePath);

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
