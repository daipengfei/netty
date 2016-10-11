package io.netty.example.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/****************************************
 * Created by daipengfei on 16/10/11.****
 ***************************************/

public class Handler implements Runnable {

    private final SocketChannel socket;
    private final SelectionKey sk;
    private ByteBuffer input = ByteBuffer.allocate(100);
    private ByteBuffer output = ByteBuffer.allocate(100);
    private static final int READING = 0, SENDING = 1;
    private int state = READING;

    Handler(Selector sel, SocketChannel c) throws IOException {
        socket = c;
        c.configureBlocking(false);
        // Optionally try first read now
        sk = socket.register(sel, 0);
        sk.attach(this);
        sk.interestOps(SelectionKey.OP_READ);
        sel.wakeup();
    }

    @Override
    public void run() {
        try {
            if (state == READING) read();
            else if (state == SENDING) send();
        } catch (IOException ex) {
            //
        }
    }

    private void read() throws IOException {
        socket.read(input);
        if (inputIsComplete()) {
            process();
            state = SENDING;
        }
    }


    private void send() throws IOException {
        socket.write(output);
        if (outputIsComplete()) sk.cancel();
    }

    private boolean inputIsComplete() {
        return true;
    }

    private boolean outputIsComplete() {
        return true;
    }

    private void process() {
        System.out.println("process!!!");
    }

}
