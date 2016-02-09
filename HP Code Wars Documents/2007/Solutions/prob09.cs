using System;
using System.Collections.Generic;
using System.Text;

namespace MusicalIntervals
{
    enum Interval
    {
        Up,
        Down
    }

    class Scale
    {
        string[] notes;
        string[] scale;
        string baseNote;

        public Scale(string inBaseNote)
        {
            baseNote = inBaseNote;
            notes = new string[] { "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#" };
            setScale();
        }

        public string BaseNote
        {
            get
            {
                return baseNote;
            }
            set
            {
                baseNote = value;
                setScale();
            }
        }

        void setScale()
        {
            scale = new string[7];
            scale[0] = baseNote;
            int i;
            for (i = 0; i < notes.Length; i++)
            {
                if (notes[i] == baseNote)
                    break;
            }

            if (i >= notes.Length)
                throw new Exception("Invalid base note provided!!!");

            // 0) Initial note (e.g. A); 
            // 1) Whole step higher (B); 
            // 2) Whole step (C#); 
            // 3) Half-step (D); 
            // 4) Whole step (E); 
            // 5) Whole step (F#); 
            // 6) Whole step (G#); 
            scale[1] = notes[(i + 2) % 12];
            scale[2] = notes[(i + 4) % 12];
            scale[3] = notes[(i + 5) % 12];
            scale[4] = notes[(i + 7) % 12];
            scale[5] = notes[(i + 9) % 12];
            scale[6] = notes[(i + 11) % 12];
        }

        public string IntervalFrom(string startNote, int interval, Interval direction)
        {
            int i;
            for( i = 0; i < scale.Length; i++ )
            {
                if( scale[i] == startNote )
                    break;
            }
            if (i >= scale.Length)
                throw new Exception("Invalid start note provided!!!");

            if (direction == Interval.Up) // Move right
            {
                interval--;
                return scale[(i + interval) % 7];
            }
            else // Move left
            {
                interval--;
                while (interval > 0)
                {
                    i--;
                    if (i < 0)
                        i = 6;
                    interval--;
                }
                return scale[i];
            }
        }
    }

    class Program
    {
        static void Main(string[] args)
        {
            string Input = System.Console.ReadLine();
            string[] STRS = Input.Split(new char[] { '+', '-' }, StringSplitOptions.RemoveEmptyEntries);
            string[] OPS = Input.Split(new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', '#', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' }, StringSplitOptions.RemoveEmptyEntries);

            Scale S = new Scale(STRS[0]);

            System.Console.Write(STRS[0] + " ");

            string s = STRS[0];
            int i;
            for (i = 1; i < STRS.Length; i++)
            {
                s = S.IntervalFrom( s, Int32.Parse(STRS[i]), OPS[i - 1] == "-" ? Interval.Down : Interval.Up );
                System.Console.Write(s + " ");
            }
            System.Console.ReadLine();
        }
    }
}
