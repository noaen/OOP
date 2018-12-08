package homework1;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * A JDailog GUI for choosing a GeoSegemnt and adding it to the route shown by
 * RoutDirectionGUI.
 * <p>
 * A figure showing this GUI can be found in homework assignment #1.
 */
public class GeoSegmentsDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;

    // the RouteDirectionsGUI that this JDialog was opened from
    private RouteFormatterGUI parent;

    // a control contained in this
    private JList<GeoSegment> lstSegments;
    JScrollPane               _listScroller;
    GeoSegment                _lastAddedSeg;

    /**
     * Creates a new GeoSegmentsDialog JDialog.
     * 
     * @effects Creates a new GeoSegmentsDialog JDialog with owner-frame owner
     *          and parent pnlParent
     */
    public GeoSegmentsDialog(Frame owner, RouteFormatterGUI pnlParent) {
        // create a modal JDialog with the an owner Frame (a modal window
        // in one that doesn't allow other windows to be active at the
        // same time).
        super(owner, "Please choose a GeoSegment", true);

        // initializing list of geosegments, panel parent and scroll pane for
        // the segments list
        this.parent = pnlParent;
        this.lstSegments = new JList<GeoSegment>(ExampleGeoSegments.segments);
        this._listScroller = new JScrollPane(this.lstSegments);
        this._lastAddedSeg = null;

        // creating panel for list of geosegments
        JPanel jp = new JPanel();
        this.add(jp);
        jp.setLayout(new BorderLayout());

        // adding the scrollpane for the segments list to the main pane
        jp.add(this._listScroller);

        // creating pane for add/cancel buttons and adding it to main pane
        JPanel bjp = new JPanel();
        bjp.setLayout(new BorderLayout());
        this.add(bjp, BorderLayout.SOUTH);

        // creating add/cancel buttons and adding them to subpanel
        JButton addButton = new JButton("Add");
        bjp.add(addButton, BorderLayout.WEST);
        JButton cancelButton = new JButton("Cancel");
        bjp.add(cancelButton, BorderLayout.EAST);

        // defining actions for the buttons
        cancelButton.addActionListener(e -> this.dispose());
        addButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        int segIdx = this.lstSegments.getSelectedIndex();
        if (segIdx != -1) { // if a segment was selected
            if (ExampleGeoSegments.segments[segIdx] != null) { // don't add a
                                                               // null segment
                if (this._lastAddedSeg == null) { // this is the first segment
                                                  // selected
                    parent.addSegment(ExampleGeoSegments.segments[segIdx]);
                    this._lastAddedSeg = ExampleGeoSegments.segments[segIdx];
                } else if (ExampleGeoSegments.segments[segIdx].getP1().equals(_lastAddedSeg.getP2())) {// if
                                                                                                       // this
                                                                                                       // is
                                                                                                       // not
                                                                                                       // the
                                                                                                       // first
                                                                                                       // selected
                                                                                                       // seg.,
                                                                                                       // need
                                                                                                       // to
                                                                                                       // check
                                                                                                       // it's
                                                                                                       // legal
                                                                                                       // to
                                                                                                       // add
                    parent.addSegment(ExampleGeoSegments.segments[segIdx]);
                    this._lastAddedSeg = ExampleGeoSegments.segments[segIdx];
                }
            }
        }
    }
}
