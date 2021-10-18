package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.cca.Cca;

/**
 * Panel containing the list of CCAs.
 */
public class CcaListPanel extends UiPart<Region> {
    private static final String FXML = "CcaListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CcaListPanel.class);

    @FXML
    private ListView<Cca> ccaListView;

    /**
     * Creates a {@code CcaListPanel} with the given {@code ObservableList}.
     */
    public CcaListPanel(ObservableList<Cca> ccaList) {
        super(FXML);
        ccaListView.setItems(ccaList);
        ccaListView.setCellFactory(listView -> new CcaListPanel.CcaListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Cca} using a {@code CcaCard}.
     */
    class CcaListViewCell extends ListCell<Cca> {
        @Override
        protected void updateItem(Cca cca, boolean empty) {
            super.updateItem(cca, empty);

            if (empty || cca == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CcaCard(cca, getIndex() + 1).getRoot());
            }
        }
    }
}
