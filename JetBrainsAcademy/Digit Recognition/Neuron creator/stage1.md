##### Theory

Recognizing digits is a hard task. Today it is a classic example of what machine learning can do. In this first stage, you will write a program that can differentiate between one and zero on a 3x3 grid.

The grid looks like this:

![](https://ucarecdn.com/1d9349d1-e0f0-4ad5-844f-a3a3dbbe4c9b/)

If you want to draw a zero here, you probably would draw this:

![](https://ucarecdn.com/163d834d-32a3-41a0-91bd-cad2c04b944b/)

And if you want to draw a one here, you probably would draw this:

![](https://ucarecdn.com/80adac5a-5cfa-4102-84ad-a9ceef4af8e3/)

Suppose you want to write a program that can take 9 numbers corresponding to these 9 cells (they contain 1 if the corresponding cell is blue and 0 if the corresponding cell is white) and output 1 or 0\. It can be pretty easy, just check all cells around the middle, and output 0, else check for the straight vertical line in the middle and output 1, right?

Well, it isn't that easy. The general problem in recognizing **handwritten** digits is that every digit can be written differently. For example, zeros may be written like this:

![](https://ucarecdn.com/f9ebdf3d-ab90-4bcf-b07d-c563eeac288d/)

And ones may be written like this:

![](https://ucarecdn.com/2e456d56-9c52-45c1-9cb1-322fb53a6131/)

Your program still should recognize these as zeros or ones.

Machine learning uses neural networks to perform difficult tasks like this. Every network consists of neurons. It always has input neurons and output neurons. Except for the input neurons, every other neuron performs simple mathematical calculations. It sums up weights of the connections multiplied by values of the neurons corresponding to these connections. Let's see an example:

![](https://ucarecdn.com/b88d806a-4bbf-4d90-b544-820192c72b1f/)

In this example, there are four neurons. Note that $w$ in the graph stands for a **weight** of the edge and $ a $ stands for a **value of the neuron**. Three of them are input neurons (it is $ a_1 $, $ a_2 $, $ a_3 $) and the last one is the output neuron ($ a_4 $). The $ a_4 $ neuron performs the mathematical operations described above. In mathematical notation it looks like this:

$ a_4 = a_1 * w_1 + a_2 * w_2 + a_3 * w_3 $

Note that if all of the weights or all of the neurons equal zero, then the value of the output neuron also equals zero. If the weights and input neurons are positive, then the output neuron is also positive, and the same goes for negative values. But in practice the value of the neuron should almost always be centered not around zero but around another value, called a **bias**. For this reason, a special input neuron should be added with value 1 with weight $ b $ (from the word bias). Take a look at the upgraded graph below:

![](https://ucarecdn.com/5b20fc2e-e40d-4e97-9049-1297e9378d44/)

Now, in mathematical notation it looks like this:

$ a_4 = a_1 * w_1 + a_2 * w_2 + a_3 * w_3 + b $

##### Description

In this stage, you need to write a function that simulates a neural network with 9 input neurons and 1 output neuron. This neural network should recognize 0 or 1 from the 3x3 grid. So, every cell from the grid is considered as input neuron. If the cell is blue then the corresponding neuron equals 1, and if the cell is white then equals 0\. For example, this grid setup:

![](https://ucarecdn.com/7c450421-9edb-4f48-b68f-96bd80cdfaf1/)

Should be mapped to the neurons like this:

![](https://ucarecdn.com/df521d20-5488-4b44-a7a3-b6e0f7ee7091/)

And it should correspond with this neural network (the values of the neurons are written to the left of the neurons):

![](https://ucarecdn.com/1ac98cf2-034b-4214-800c-cecef5e94347/)

For actual machine learning, the computer should figure out the best weights and biases on its own (using an algorithm written by a programmer, of course), but in this stage, you will not write this algorithm. Instead, use these pre-calculated (pretty accurate) weights:

$ w_1 = 2, w_2 = 1, w_3 = 2, w_4 = 4, w_5 = -4, $

$ w_6 = 4, w_7 = 2, w_8 = -1, w_9 = 2, b = -5 $

For these weights, you can interpret a value of the $ a_{10} $ neuron pretty easily. If it is positive then the result is zero, else the result is one. For example, for the grid above:

$ a_{10} = 1 * 2 + 1 * 1 + 0 * 2 + 1 * 4+ 0 * -4+ 1 * 4 + 0 * 2 + 1 * -1 + 0 * 2 -5 = 5 $

The result is positive, so the number above is 0\. Another example, for this grid:

![](https://ucarecdn.com/7915c5b5-672d-4092-aa16-dc6f5ff905f4/)

$ a_{10}=1∗2+1∗1+0∗2+0∗4+1∗−4+0∗4+0∗2+1∗−1+0∗2−5=−7 $

The result is negative, so the number above is 1\. It's pretty fantastic that this simple math can differentiate zero or one on the 3x3 image!

##### Examples

The example below shows how your output should look.

    Input grid:
    _X_
    _X_
    XX_
    This number is 1

    Input grid :
    _XX
    X_X
    XXX
    This number is 0

    Input grid:
    _X_
    _X_
    X__
    This number is 1

    Input grid:
    _X_
    X_X
    _X_
    This number is 0