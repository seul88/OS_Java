package put.os.memory;

/**
 * Created by Damian on 10.12.2016.
 */
public class Address {
    private int page;
    private int shift;

    public Address(int page, int shift) {
        this.page = page;
        this.shift = shift;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public int asSimpleInt () {
        return page * MemoryManagementUnit.pageSize+shift;
    }
}
